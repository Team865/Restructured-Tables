from .client import TBACachedSession


class _TBAQueryWrapper:

    @classmethod
    def query_wrapper(cls, query_url_function):
        def fetch_query(self: "TBARawAPI", *_, **kwargs) -> "dict":
            url = self._session.query_args.create_url(query_url_function(self), **kwargs)
            return getattr(self._session, url)

        return fetch_query


class TBARawAPI:
    _query_wrapper = _TBAQueryWrapper.query_wrapper

    def __init__(self, session: "TBACachedSession"):
        self._session = session

    # Status

    @_query_wrapper
    def status(self):
        return "/status"

    # Teams/Team API

    @_query_wrapper
    def teams_by_page_num(self):
        return "/teams/{page_num}"

    @_query_wrapper
    def teams_by_page_num_simple(self):
        return "/teams/{page_num}/simple"

    @_query_wrapper
    def teams_by_page_num_keys(self):
        return "/teams/{page_num}/keys"

    @_query_wrapper
    def teams_in_year_by_page_num(self):
        return "/teams/{year}/{page_num}"

    @_query_wrapper
    def teams_in_year_by_page_num_simple(self):
        return "/teams/{year}/{page_num}/simple"

    @_query_wrapper
    def teams_in_year_by_page_num_keys(self):
        return "/teams/{year}/{page_num}/keys"

    @_query_wrapper
    def team(self):
        return "/team/{team_key}"

    @_query_wrapper
    def team_simple(self):
        return "/team/{team_key}"

    @_query_wrapper
    def team_years_participated(self):
        return "/team/{team_key}"

    @_query_wrapper
    def team_districts(self):
        return "/team/{team_key}"

    @_query_wrapper
    def team_robots(self):
        return "/team/{team_key}"

    @_query_wrapper
    def team_awards(self):
        return "/team/{team_key}/awards"

    @_query_wrapper
    def team_awards_year(self):
        return "/team/{team_key}/awards/{year}"

    @_query_wrapper
    def team_matches_in_year(self):
        return "/team/{team_key}/matches/{year}"

    @_query_wrapper
    def team_matches_in_year_simple(self):
        return "/team/{team_key}/matches/{year}/simple"

    @_query_wrapper
    def team_matches_in_year_keys(self):
        return "/team/{team_key}/matches/{year}/keys"

    @_query_wrapper
    def team_media_in_year(self):
        return "/team/{team_key}/media/{year}"

    @_query_wrapper
    def team_media_by_tag(self):
        return "/team/{team_key}/media/tag/{media_tag}"

    @_query_wrapper
    def team_media_by_tag_in_year(self):
        return "/team/{team_key}/media/tag/{media_tag}/{year}"

    @_query_wrapper
    def team_social_media(self):
        return "/team/{team_key}/social_media"

    @_query_wrapper
    def team_events(self):
        return "/team/{team_key}/events"

    @_query_wrapper
    def team_events_simple(self):
        return "/team/{team_key}/events/simple"

    @_query_wrapper
    def team_events_keys(self):
        return "/team/{team_key}/events/keys"

    @_query_wrapper
    def team_events_in_year(self):
        return "/team/{team_key}/events/{year}"

    @_query_wrapper
    def team_events_in_year_simple(self):
        return "/team/{team_key}/events/{year}/simple"

    @_query_wrapper
    def team_events_in_year_keys(self):
        return "/team/{team_key}/events/{year}/keys"

    @_query_wrapper
    def team_events_in_year_statuses(self):
        return "/team/{team_key}/events/{year}/statuses"

    @_query_wrapper
    def team_event_matches(self):
        return "/team/{team_key}/event/{event_key}/matches"

    @_query_wrapper
    def team_event_matches_simple(self):
        return "/team/{team_key}/event/{event_key}/matches/simple"

    @_query_wrapper
    def team_event_matches_keys(self):
        return "/team/{team_key}/event/{event_key}/matches/keys"

    @_query_wrapper
    def team_event_awards(self):
        return "/team/{team_key}/event/{event_key}/awards"

    @_query_wrapper
    def team_event_status(self):
        return "/team/{team_key}/event/{event_key}/status"

    # Event API

    @_query_wrapper
    def events_in_year(self):
        return "/events/{year}"

    @_query_wrapper
    def events_in_year_simple(self):
        return "/events/{year}/simple"

    @_query_wrapper
    def events_in_year_keys(self):
        return "/events/{year}/keys"

    @_query_wrapper
    def event(self):
        return "/event/{event_key}"

    @_query_wrapper
    def event_simple(self):
        return "/event/{event_key}/simple"

    @_query_wrapper
    def event_alliances(self):
        return "/event/{event_key}/alliances"

    @_query_wrapper
    def event_insights(self):
        return "/event/{event_key}/insights"

    @_query_wrapper
    def event_oprs(self):
        return "/event/{event_key}/oprs"

    @_query_wrapper
    def event_predictions(self):
        return "/event/{event_key}/predictions"

    @_query_wrapper
    def event_rankings(self):
        return "/event/{event_key}/rankings"

    @_query_wrapper
    def event_district_points(self):
        return "/event/{event_key}/district_points"

    @_query_wrapper
    def event_teams(self):
        return "/event/{event_key}/teams"

    @_query_wrapper
    def event_teams_simple(self):
        return "/event/{event_key}/teams/simple"

    @_query_wrapper
    def event_teams_keys(self):
        return "/event/{event_key}/teams/keys"

    @_query_wrapper
    def event_teams_statuses(self):
        return "/event/{event_key}/teams/statuses"

    @_query_wrapper
    def event_matches(self):
        return "/event/{event_key}/matches"

    @_query_wrapper
    def event_matches_simple(self):
        return "/event/{event_key}/matches/simple"

    @_query_wrapper
    def event_matches_keys(self):
        return "/event/{event_key}/matches/keys"

    @_query_wrapper
    def event_matches_timeseries(self):
        return "/event/{event_key}/matches/timeseries"

    @_query_wrapper
    def event_awards(self):
        return "/event/{event_key}/awards"

    # Match API

    @_query_wrapper
    def match(self):
        return "/match/{match_key}"

    @_query_wrapper
    def match_simple(self):
        return "/match/{match_key}/simple"

    @_query_wrapper
    def match_timeseries(self):
        return "/match/{match_key}/timeseries"

    # District API

    @_query_wrapper
    def districts_in_year(self):
        return "/districts/{year}"

    @_query_wrapper
    def district_events(self):
        return "/district/{district_key}/events"

    @_query_wrapper
    def district_events_simple(self):
        return "/district/{district_key}/events/simple"

    @_query_wrapper
    def district_events_keys(self):
        return "/district/{district_key}/events/keys"

    @_query_wrapper
    def district_teams(self):
        return "/district/{district_key}/teams"

    @_query_wrapper
    def district_teams_simple(self):
        return "/district/{district_key}/teams/simple"

    @_query_wrapper
    def district_teams_keys(self):
        return "/district/{district_key}/teams/keys"

    @_query_wrapper
    def district_rankings(self):
        return "/district/{district_key}/rankings"
