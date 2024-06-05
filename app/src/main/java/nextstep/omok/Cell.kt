package nextstep.omok

interface Cell {
    var position: Pair<Int, Int>?
    fun isEmpty(): Boolean
}