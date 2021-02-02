package pl.portfolio.todomvvm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.portfolio.todomvvm.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dishes"))
                dao.insert(Task("Call grandma", important = true))
                dao.insert(Task("Walk the dog", completed = true))
                dao.insert(Task("Buy groceries", completed = true))
                dao.insert(Task("Wash a car"))
            }
        }
    }
}