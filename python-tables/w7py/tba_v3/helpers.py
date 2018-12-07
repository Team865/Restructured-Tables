from typing import *

import pandas as pd

from .api import TBARawAPI
from .client import TBACachedSession, TBAQueryArguments
from .exceptions import *

__all__ = ["query_args", "event_helper"]


class TBABaseHelper:
    def __init__(self, session: "TBACachedSession"):
        self._api = TBARawAPI(session)

    def _get_api(self) -> "TBARawAPI":
        return self._api

    def _has_error(self, req, **kw):
        return "Errors" in getattr(self._api, req)(**kw).keys()


class TBAEventHelper(TBABaseHelper):
    def __init__(self, session: "TBACachedSession"):
        super().__init__(session)
        if "event_key" not in session.query_args.query_args_dict:
            raise TBARequiredArgumentNotError("Cannot use TBAEventHelper without an event")
        self.event_key = session.query_args.query_args_dict["event_key"]

    def check_validity(self):
        if self._has_error("event"):
            raise TBAInvalidKeyError("Invalid Event Key")

    def list_matches(self, simple: "bool" = False) -> "dict":
        if simple:
            return self._api.event_matches_simple()
        else:
            return self._api.event_matches()

    def qualification_match_schedule(self,
                                     use_df=True,
                                     df_one_indexed=True,
                                     transpose=False,
                                     convert_to_int=True):

        positions = [(colour, number) for colour in ["Red", "Blue"] for number in [0, 1, 2]]
        qualification_matches = {match["match_number"]: match
                                 for match in self.list_matches(simple=True) if match["comp_level"] == "qm"}

        schedule_rows = []
        for match_number in sorted(qualification_matches.keys()):
            match_data = qualification_matches[match_number]
            schedule_row = {}
            for colour, number in positions:
                match_key = match_data["alliances"][colour.lower()]["team_keys"][number]
                if convert_to_int:
                    match_value = int(match_key[3:])
                else:
                    match_value = match_key
                schedule_row["{} {}".format(colour, number + 1)] = match_value
            schedule_rows.append(schedule_row)

        columns = ["{} {}".format(colour, number + 1) for colour, number in positions]

        if use_df:
            table = pd.DataFrame(schedule_rows, columns=columns)
            table.index.name = "Match #"
            if df_one_indexed:
                table.index += 1
            if transpose:
                return table.T
            else:
                return table
        else:
            if transpose:
                return {column: [row[column] for row in schedule_rows] for column in columns}
            else:
                return schedule_rows


def query_args(*args,
               team_key: "Union[str, int]" = "",
               team: "Any" = None,
               district_key: "str" = "",
               district: "Any" = None,
               match_key: "str" = "",
               match: "Any" = None,
               event_key: "str" = "",
               event: "Any" = None,
               year: "Union[str, int]" = "",
               media_tag: "str" = "",
               page_num: "Union[str, int]" = "") -> "TBAQueryArguments":
    _args = {}
    if team_key:
        _args["team_key"] = team_key
    elif team:
        _args["team_key"] = team
    if district_key:
        _args["district_key"] = district_key
    elif district:
        _args["district_key"] = district
    if match_key:
        _args["match_key"] = match_key
    elif match:
        _args["match_key"] = match
    if event_key:
        _args["event_key"] = event_key
    elif event:
        _args["event_key"] = event
    if year:
        _args["year"] = year
    if media_tag:
        _args["media_tag"] = media_tag
    if page_num:
        _args["page_num"] = page_num
    if args:
        for arg in args:
            if type(arg) is TBAQueryArguments:
                _args = arg.updated(_args)
    return TBAQueryArguments(**_args)


event_helper = TBAEventHelper
