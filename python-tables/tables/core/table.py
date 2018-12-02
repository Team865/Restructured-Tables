generators = []


def table(*_, rows="entry", level=0, requires_tba=False):
    rows = rows.lower().strip()
    assert rows in {"entry", "team", "match", "scout", "event"}
    assert level >= 0

    def table_factory(row_func):
        generators.append((row_func, rows, level, requires_tba))
        return None
    return table_factory
