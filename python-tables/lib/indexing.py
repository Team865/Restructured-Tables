# implementation of index objects

import re


class EventIndex(str):
    def __getattr__(self, item):
        if item == "year":
            return int(re.findall(r"\d+", self)[0])
        if item == "event_code":
            return re.findall(r"[a-zA-Z]", self)[0]


class MatchIndex(str):
    def __getattr__(self, item):
        if item == "number":
            return int(re.findall(r"\d+", self)[1])
        if item == "match_type":
            return re.findall(r"[a-zA-Z]+", self)[1]
        if item == "event":
            return EventIndex(self.split("_")[0])


class TeamIndex(str):
    def __getattr__(self, item):
        if item == "number":
            return int(self[3:])


class ScoutIndex(str):
    def __getattr__(self, item):
        if item == "name":
            return self.split("_")[1]


class PositionIndex(str):
    def __getattr__(self, item):
        if item == "alliance":
            return self[:-1]
        if item == "number":
            return self[-1:]


class EntryIndex(str):
    def __getattr__(self, item):
        if item == "scout":
            return ScoutIndex("".join(self.split("_")[0:1]))
        if item == "team":
            return TeamIndex(self.split("_")[2])
        if item == "position":
            return PositionIndex(self.split("_")[3])
        if item == "match":
            return MatchIndex("".join(self.split("_")[4:5]))