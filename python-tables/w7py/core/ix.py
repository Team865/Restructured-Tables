import re

__all__ = ["team_", "match_", "event_"]


class _LookupIndexer:

    def make_tba_string(self):
        pass

    def parse_tba_string(self, tba_string):
        pass


class _TeamIndexer(_LookupIndexer):
    def __init__(self, other):
        try:
            self._team_number = int(other)
        except (TypeError, ValueError):
            if type(other) is str:
                self.parse_tba_string(other)
            else:
                raise ValueError("Cannot interpret team")

    def __int__(self):
        return self._team_number

    def __str__(self):
        return "<Team {}>".format(self._team_number)

    def __eq__(self, other):
        try:
            if type(other) is _TeamIndexer:
                return self._team_number == int(other)
            else:
                return self._team_number == int(_TeamIndexer(other))
        except TypeError:
            return False

    def __ne__(self, other):
        return not self == other

    def __lt__(self, other):
        try:
            if type(other) is _TeamIndexer:
                return self._team_number < int(other)
            else:
                return self._team_number < int(_TeamIndexer(other))
        except TypeError:
            return False

    def __gt__(self, other):
        try:
            if type(other) is _TeamIndexer:
                return self._team_number > int(other)
            else:
                return self._team_number > int(_TeamIndexer(other))
        except TypeError:
            return False

    def __le__(self, other):
        return not self > other

    def __ge__(self, other):
        return not self < other

    def make_tba_string(self):
        return "frc{}".format(self._team_number)

    def parse_tba_string(self, tba_string):
        if re.match("^frc\d{2,4}$", tba_string) is not None:
            self._team_number = int(tba_string[3:])
        else:
            raise ValueError("Cannot Parse TBA String for Team")


class _MatchIndexer(_LookupIndexer):
    COMP_LEVEL_SHORT = ("q", "ef", "qf", "sf", "f")
    COMP_LEVEL_LONG = ("Qualification", "Elimination Finals", "Quarterfinals", "Semifinals", "Finals")

    def __init__(self, other, level=COMP_LEVEL_SHORT[0]):
        if type(other) is _MatchIndexer:
            self._match_number = other.match_number()
            self._comp_level = other.comp_level()
        elif type(other) is str:
            self.parse_tba_string(other)
        else:
            if level not in self.COMP_LEVEL_SHORT:
                raise ValueError("Cannot Interpret Match Level")
            self._comp_level = level
            try:
                self._match_number = int(other)
            except TypeError:
                raise ValueError("Cannot Interpret Match")

    def __int__(self):
        return self.match_number()

    def __str__(self):
        return "<{} Match #{}>".format(self.long_comp_level(), self._match_number)

    def __eq__(self, other):
        if type(other) is _MatchIndexer:
            return self.make_tba_string() == other.make_tba_string()
        return False

    def __ne__(self, other):
        return not self == other

    def __lt__(self, other):
        if type(other) is _MatchIndexer:
            return (self.COMP_LEVEL_SHORT.index(self._comp_level) <
                    self.COMP_LEVEL_SHORT.index(other.comp_level())
                    or self._match_number < other.match_number())
        raise TypeError("Cannot Compare an Non-Match Object")

    def __gt__(self, other):
        if type(other) is _MatchIndexer:
            return (self.COMP_LEVEL_SHORT.index(self._comp_level) >
                    self.COMP_LEVEL_SHORT.index(other.comp_level())
                    or self._match_number > other.match_number())
        raise TypeError("Cannot Compare an Non-Match Object")

    def __le__(self, other):
        return not self > other

    def __ge__(self, other):
        return not self < other

    def long_comp_level(self):
        try:
            return self.COMP_LEVEL_LONG[self.COMP_LEVEL_SHORT.index(self._comp_level)]
        except IndexError:
            raise ValueError("Cannot Interpret Match Level")

    def match_number(self):
        return self._match_number

    def comp_level(self):
        return self._comp_level


class _EventIndexer(_LookupIndexer):
    def __init__(self, other, name=None):
        if type(other) is _EventIndexer:
            self._name = other.name()
            self._event_code = other.event_code()
        elif type(other) is str:
            self.parse_tba_string(other)
            if name is not None:
                self._name = name
        else:
            raise ValueError("Cannot Interpret Event")

    def __str__(self):
        return "<Event {}>".format(self._name)

    def __eq__(self, other):
        if type(other) is _EventIndexer:
            return self._event_code == other.event_code()
        else:
            try:
                return self._event_code == _EventIndexer(other).event_code()
            except ValueError:
                return False

    def __ne__(self, other):
        return not self == other

    def name(self):
        return self._name

    def event_code(self):
        return self._event_code

    def make_tba_string(self):
        return self._event_code

    def parse_tba_string(self, tba_string):
        self._event_code = tba_string
        self._name = "Unnamed - {}".format(tba_string)


class _TBAWrappedMatchInEventIndexer(_MatchIndexer):
    def __init__(self, other, event=None, level=_MatchIndexer.COMP_LEVEL_SHORT[0]):
        super().__init__(other, level)
        if type(other) is _TBAWrappedMatchInEventIndexer:
            self._event = other.event()
        else:
            self._event = _EventIndexer(event)

    def make_tba_string(self):
        return "{}{}".format(super().make_tba_string(),
                             self._event.make_tba_string())

    def parse_tba_string(self, tba_string):
        super().make_tba_string()
        self._event.make_tba_string()


team_, match_, event_ = _TeamIndexer, _MatchIndexer, _EventIndexer
