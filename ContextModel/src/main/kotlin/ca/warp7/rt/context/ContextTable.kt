package ca.warp7.rt.context

/**
 * Tables are used to represent specific entities of data that are part of a Context.
 * A Table is structured as a 2 dimensional data frame with column headers but no specific
 * row index, where the order of both is irrelevant. A Context contains a set of Tables,
 * each with a distinct row type and set of columns (i.e. data points). For example,
 * in the Context of a specific FRC event, there may be a Table for each match in the event,
 * a Table for each team's stats in the event, a Table for each person who was scouting,
 * and a Table for each entry of data (e.g. team per scout per match)
 */
interface ContextTable {
}