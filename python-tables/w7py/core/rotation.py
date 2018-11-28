class ScoutingRotation:

    def __init__(self, schedule: dict):
        self.schedule = {}
        self.sorted_matches = []
        self.teams_set = set()
        self.priority_teams = []
        self.scouts_set = set()
        self.scout_schedule = {}
        self.scout_schedule_by_match = {}
        self.distribution_schedule = {}
        self.virtual_schedule = {}
        self.virtual_schedule_by_match = {}
        self.weighted_schedule = {}
        self.scouted_matches_count = {}

        self.expected_entries = 0
        self.scouted_match_limit = 0
        self.entries_limit = 0
        self.scouts_limit = 0
        self.available_ratio = 1.0

        self.set_schedule(schedule)
        self.set_available(6)

    def set_schedule(self, schedule):
        self.schedule = schedule
        self.sorted_matches = sorted(schedule.keys())
        self.teams_set = set().union(*schedule.values())

    def set_available(self, scout_limit: int, ratio: float = 1.0):
        total_matches = len(self.sorted_matches)
        self.available_ratio = ratio
        self.expected_entries = int(total_matches * scout_limit * ratio)
        self.entries_limit = int(self.expected_entries / len(self.teams_set))
        self.scouted_match_limit = min(self.entries_limit, int(total_matches * 6 / len(self.teams_set)))
        self.scouts_limit = scout_limit

    def set_scouted_match_limit(self, limit: int):
        self.scouted_match_limit = max(limit, 0)
        self.scouts_limit = 6

    def set_priority_teams(self, teams: list):
        self.priority_teams.clear()
        for team in teams:
            if team in self.teams_set:
                self.priority_teams.append(team)

    def set_scouts(self, scouts):
        self.scouts_set = set(scouts)

    def reset_weights(self):
        self.weighted_schedule = {m: [0] * 6 for m in self.sorted_matches}
        self.scouted_matches_count = {t: 0 for t in self.teams_set}

    def reset_virtual_schedule(self):
        scout_indices = list(range(self.scouts_limit))
        self.virtual_schedule_by_match = {m: [[] for _ in range(6)] for
                                          m in self.sorted_matches}
        self.virtual_schedule = {scout: [] for scout in scout_indices}

    def reset_distribution_schedule(self):
        self.distribution_schedule = {m: [0] * 6 for m in self.sorted_matches}

    def assert_weights(self):
        if not self.weighted_schedule:
            self.reset_weights()

    @staticmethod
    def debug_pp(o):
        import io
        import pprint
        with io.StringIO() as buf:
            pprint.pprint(o, buf, width=200)
            print(buf.getvalue())

    def debug_weighted_percentage(self):
        actual_entries = 0
        for weights in self.weighted_schedule.values():
            for weight in weights:
                if weight > 0:
                    actual_entries += 1
        print("actual entries: {}/{} ({:4f}%)"
              .format(actual_entries,
                      self.expected_entries,
                      actual_entries / self.expected_entries * 100))

    def debug_weights(self):
        self.debug_pp(self.weighted_schedule)

    def debug_matches_scouted_count(self):
        self.debug_pp(self.scouted_matches_count)

    def debug_distribution_schedule(self):
        self.debug_pp(self.distribution_schedule)

    def debug_virtual_schedule(self):
        self.debug_pp(self.virtual_schedule)

    def debug_virtual_schedule_by_match(self):
        self.debug_pp(self.virtual_schedule_by_match)

    def debug_virtual_scout_count(self):
        print(list(zip(self.virtual_schedule.keys(),
                       list(map(len, self.virtual_schedule.values())))))

    def min_weight_order(self):
        return sorted(self.teams_set, key=lambda x: self.scouted_matches_count[x])

    def set_checked_weight(self, match, team):
        i = self.schedule[match].index(team)
        if self.weighted_schedule[match][i] > 0:
            self.weighted_schedule[match][i] += 1
        else:
            available_scouts = self.scouts_limit
            for weight in self.weighted_schedule[match]:
                if weight > 0:
                    available_scouts -= 1
            if available_scouts > 0 and self.scouted_matches_count[team] < self.scouted_match_limit:
                self.weighted_schedule[match][i] += 1
                self.scouted_matches_count[team] += 1

    def weight_match(self, idx, lt):
        match = self.sorted_matches[idx]
        for team in self.schedule[match]:
            if team == lt:
                self.set_checked_weight(match, team)
                return True
        return False

    def weight_loop_up(self, looping_team: int, idx_max: int):
        if not self.scouted_matches_count[looping_team] < self.scouted_match_limit:
            return
        idx = idx_max
        counted_limit = self.scouted_match_limit
        while counted_limit > 0 and idx >= 0:
            if self.weight_match(idx, looping_team):
                counted_limit -= 1
            idx -= 1
            if idx == 0:
                idx = idx_max - 1
                counted_limit -= 1

    def weight_loop_down(self, looping_team: int, idx_min: int):
        if not self.scouted_matches_count[looping_team] < self.scouted_match_limit:
            return
        idx = idx_min
        counted_limit = self.scouted_match_limit
        total_matches = len(self.sorted_matches)
        while counted_limit > 0 and idx < total_matches:
            if self.weight_match(idx, looping_team):
                counted_limit -= 1
            idx += 1
            if idx == total_matches:
                idx = idx_min - 1
                counted_limit -= 1

    def weight_by_match_appearance(self, appeared_team: int):
        for match in self.sorted_matches:
            if appeared_team in self.schedule[match]:
                start_idx = self.sorted_matches.index(match)
                for looping_team in self.schedule[match]:
                    self.weight_loop_up(looping_team, start_idx)

    def weight_priorities(self):
        self.assert_weights()
        for team in self.priority_teams:
            self.weight_by_match_appearance(team)

    def weight_backwards(self):
        self.assert_weights()
        idx = len(self.sorted_matches) - 1
        for team in self.min_weight_order():
            self.weight_loop_up(team, idx)

    def weight_forwards(self):
        self.assert_weights()
        for team in self.min_weight_order():
            self.weight_loop_down(team, 0)

    def priority_in_match(self, match):
        for team in self.schedule[match]:
            if team in self.priority_teams:
                return True
        return False

    def normalize_weights(self):
        normal_max = 0
        for match in self.sorted_matches:
            if not self.priority_in_match(match):
                for i, testing_team in enumerate(self.schedule[match]):
                    if self.weighted_schedule[match][i] > normal_max:
                        normal_max = self.weighted_schedule[match][i]
            else:
                match_normal_max = 0
                for i, testing_team in enumerate(self.schedule[match]):
                    if testing_team not in self.priority_teams and \
                            self.weighted_schedule[match][i] > match_normal_max:
                        match_normal_max = self.weighted_schedule[match][i]
                for i, weight in enumerate(self.weighted_schedule[match]):
                    self.weighted_schedule[match][i] = min(weight, match_normal_max)
        for match in self.sorted_matches:
            for i, weight in enumerate(self.weighted_schedule[match]):
                self.weighted_schedule[match][i] = min(weight, normal_max)

    def calculate_weighted_schedule(self):
        self.weight_priorities()
        self.weight_backwards()
        self.weight_forwards()
        self.normalize_weights()

    def calculate_distribution_schedule(self):
        self.assert_weights()
        self.reset_distribution_schedule()
        for team in self.teams_set:
            assigned_matches_count = self.scouted_matches_count[team]
            extra_entries = self.entries_limit - assigned_matches_count
            team_max_weight = 0
            for match in self.sorted_matches:
                if team in self.schedule[match]:
                    i = self.schedule[match].index(team)
                    position_weight = self.weighted_schedule[match][i]
                    if position_weight > 0:
                        if position_weight > team_max_weight:
                            team_max_weight = position_weight
                        self.distribution_schedule[match][i] += 1
            while extra_entries > 0:
                any_equals = False
                for match in self.sorted_matches:
                    if team in self.schedule[match]:
                        i = self.schedule[match].index(team)
                        position_weight = self.weighted_schedule[match][i]
                        if position_weight == 0:
                            if position_weight > team_max_weight:
                                team_max_weight = position_weight
                            self.distribution_schedule[match][i] += 1
                extra_entries -= 1

    def calculate_virtual_schedule(self):
        self.assert_weights()
        self.reset_virtual_schedule()

        pos_indices = list(range(6))
        scout_idx = list(range(self.scouts_limit))

        for match in self.sorted_matches:
            match_weights = self.weighted_schedule[match]
            match_available = sorted(scout_idx, key=lambda x: len(self.virtual_schedule[x]))
            pos_indices = sorted(pos_indices, key=lambda x: match_weights[x], reverse=True)
            for pos_index in pos_indices:
                if match_weights[pos_index] > 0 and len(match_available) > 0:
                    scout = match_available.pop(0)
                    self.virtual_schedule_by_match[match][pos_index].append(scout)
                    self.virtual_schedule[scout].append(
                        (match, self.schedule[match][pos_index], pos_index))


if __name__ == '__main__':
    import sys

    sys.path.append("C:/Users/Yu/PycharmProjects/w7py/tests")
    qms = __import__("iri_qms").iri_qms
    r = ScoutingRotation(qms)
    r.set_available(30, 0.5)
    r.set_priority_teams([865])
    r.calculate_weighted_schedule()
    r.debug_weights()
    r.debug_matches_scouted_count()
    r.debug_weighted_percentage()
    r.calculate_distribution_schedule()
    r.debug_distribution_schedule()
    # r.calculate_virtual_schedule()
    # r.debug_virtual_scout_count()
