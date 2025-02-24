import java.util.Scanner


class NotesConsoleApp {
    private val archives = mutableListOf<Archive>()

    fun showArchives() {
        printOptions(
            message = "Список архивов:",
            actions = listOf("Создать архив"),
            entries = archives,
        )
        goNext(archives.size + 1, null, null)
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

    private fun createEntry(archiveIndex: Int?) {
        val title = askForNonEmptyString(
            "Введите название",
            "Название не может быть пустым"
        )
        if (archiveIndex == null) {
            archives.add(Archive(title))
            println("Архив $title создан")
            return
        }
        val content = askForNonEmptyString(
            "Введите содержание заметки",
            "Содержание заметки не может быть пустым"
        )
        archives[archiveIndex].notes.add(Note(title, content))
        println("Заметка '$title' создана")
    }

    private fun showNotes(archiveIndex: Int) {
        printOptions(
            message = "\nАрхив: ${archives[archiveIndex]}",
            actions = listOf("Создать заметку"),
            entries = archives[archiveIndex].notes,
        )
        goNext(archives[archiveIndex].notes.size + 1, archiveIndex, null)
    }

    private fun goNext(numOfOptions: Int, archiveIndex: Int?, noteIndex: Int?) {
        when (val userInput = getUserInput(numOfOptions)) {
            "0" -> when {
                archiveIndex != null && noteIndex != null -> showNotes(archiveIndex)
                archiveIndex != null -> {
                    createEntry(archiveIndex)
                    showNotes(archiveIndex)
                }

                else -> {
                    createEntry(null)
                    showArchives()
                }
            }

            in "1"..(numOfOptions - 1).toString() -> when {
                archiveIndex != null -> showNoteContent(archiveIndex, userInput.toInt() - 1)
                else -> showNotes(userInput.toInt() - 1)
            }

            numOfOptions.toString() -> if (archiveIndex != null) showArchives() else println("Выход")

            else -> {
                println(userInput)
                when {
                    archiveIndex != null && noteIndex != null -> showNoteContent(
                        archiveIndex,
                        noteIndex
                    )

                    archiveIndex != null -> showNotes(archiveIndex)
                    else -> showArchives()
                }
            }
        }
    }

    private fun showNoteContent(archiveIndex: Int, noteIndex: Int) {
        printOptions(
            message = "Заметка: ${archives[archiveIndex].notes[noteIndex].title}\n" +
                    "${archives[archiveIndex].notes[noteIndex].content}\n",
            actions = listOf(),
            entries = listOf(),
        )
        goNext(0, archiveIndex, noteIndex)
    }
}
