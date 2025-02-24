abstract class Entry(val title: String) {
    override fun toString(): String {
        return title
    }
}

class Archive(title: String) : Entry(title) {
    val notes = mutableListOf<Note>()
}

class Note(title: String, var content: String) : Entry(title)
