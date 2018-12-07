generators = []


def table(rows, *_, level=0):
    def table_factory(row_func):
        generators.append((row_func, rows, level))
        return None

    rows = rows.lower().strip()
    assert rows in {"entry", "team", "match", "scout", "event"}
    assert level >= 0
    return table_factory
