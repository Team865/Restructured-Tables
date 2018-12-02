generators = []


def table(rows: "str", *_, level: "int" = 0, requires_tba: "bool" = False) -> "function":
    rows = rows.lower().strip()
    assert rows in {"entry", "team", "match", "scout", "event"}
    assert level >= 0

    def table_factory(row_func):
        generators.append((row_func, rows, level, requires_tba))
        return None

    return table_factory


@table("entry", level=1)
def auto_types(src, entry):
    series = src(entry.Index).series
    plates = src(entry.match).tba['score_breakdown'][entry.alliance.lower()]['tba_gameData']
    if plates[0] == "L":
        switch_plate = "Left"
    else:
        switch_plate = "Right"
    if plates[1] == "R":
        scale_plate = "Left"
    else:
        scale_plate = "Right"
    auto_line = series["Auto line"].final
    starting_pos = series["Start position"].final
    scale = series["Auto scale success"].count
    switch = series["Auto switch success"].count
    if auto_line and starting_pos != "None":
        if scale > 0:
            if starting_pos == "Center":
                auto_type = "Center Scale"
            elif starting_pos == scale_plate:
                auto_type = "Near Scale"
            else:
                auto_type = "Far Scale"
        elif switch > 0:
            if starting_pos == "Center":
                auto_type = "Center Switch"
            else:
                auto_type = "Side Switch"
        else:
            auto_type = "Auto Line"
    else:
        auto_type = "None"
    return {
        "auto_line": auto_line,
        "start_position": starting_pos,
        "scale_plate": scale_plate,
        "switch_plate": switch_plate,
        "auto_scale": scale,
        "auto_switch": switch,
        "auto_type": auto_type
    }