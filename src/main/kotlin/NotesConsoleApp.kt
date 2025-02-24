import java.util.Scanner

class NotesConsoleApp {
    val archives = mutableListOf<Archive>()

    fun showArchives() {
        printOptions(
            message = "Список архивов:",
            actions = listOf("Создать архив"),
            entries = archives,
        )
        val numOfOptions = archives.size + 1
        when (val userInput = getUserInput(numOfOptions)) {
            "0" -> {
                createArchive()
                showArchives()
            }

            in "1"..(numOfOptions - 1).toString() -> {
                showNotes(userInput.toInt() - 1)
            }

            numOfOptions.toString() -> println("Выход")
            else -> {
                println(userInput)
                showArchives()
            }
        }
    }

    private fun printOptions(message: String, actions: List<String>, entries: List<Entry>) {
        println(message)
        actions.forEachIndexed { index, action -> println("$index. $action") }
        val numOfActions = actions.size
        entries.forEachIndexed { index, entry -> println("${index + numOfActions} - $entry") }
        println("${entries.size + numOfActions} - Выход")
    }

    private fun getUserInput(numOfOptions: Int): String {
        val userInput = Scanner(System.`in`).nextLine()
        if (userInput.toIntOrNull() == null) {
            return "Следует вводить только цифры"
        }
        if (userInput.toInt() !in 0..numOfOptions) {
            return "Нет такой опции"
        }
        return userInput
    }

    private fun askForNonEmptyString(message: String, complains: String): String {
        var userText = ""
        while (userText.isEmpty()) {
            println(message)
            userText = Scanner(System.`in`).nextLine().trim()
            if (userText.isEmpty()) {
                println(complains)
            }
        }
        return userText
    }

    private fun createArchive() {

        val archiveTitle = askForNonEmptyString(
            "Введите название архива",
            "Название архива не может быть пустым"
        )
        archives.add(Archive(archiveTitle))
        println("Архив $archiveTitle создан")
    }

    private fun showNotes(archiveIndex: Int) {
        printOptions(
            message = "\nАрхив: ${archives[archiveIndex]}",
            actions = listOf("Создать заметку"),
            entries = archives[archiveIndex].notes,
        )
        val numOfOptions = archives[archiveIndex].notes.size + 1
        when (val userInput = getUserInput(numOfOptions)) {
            "0" -> {
                createNote(archiveIndex)
                showNotes(archiveIndex)
            }

            in "1"..(numOfOptions - 1).toString() -> {
                showNoteContent(archiveIndex, userInput.toInt() - 1)
            }

            numOfOptions.toString() -> showArchives()
            else -> {
                println(userInput)
                showNotes(archiveIndex)
            }
        }
    }

    private fun createNote(archiveIndex: Int) {
        val noteTitle = askForNonEmptyString(
            "Введите название заметки",
            "Название заметки не может быть пустым"
        )
        val noteContent = askForNonEmptyString(
            "Введите содержание заметки",
            "Содержание заметки не может быть пустым"
        )
        archives[archiveIndex].notes.add(Note(noteTitle, noteContent))
        println("Заметка '$noteTitle' создана")
    }

    private fun showNoteContent(archiveIndex: Int, noteIndex: Int) {
        printOptions(
            message = "Заметка: ${archives[archiveIndex].notes[noteIndex].title}\n" +
                    "${archives[archiveIndex].notes[noteIndex].content}\n",
            actions = listOf(),
            entries = listOf(),
        )
        when (val userInput = getUserInput(0)) {
            "0" -> showNotes(archiveIndex)
            else -> {
                println(userInput)
                showNoteContent(archiveIndex, noteIndex)
            }
        }
    }
}
