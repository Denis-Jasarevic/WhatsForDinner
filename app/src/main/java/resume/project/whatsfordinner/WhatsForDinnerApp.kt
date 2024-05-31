package resume.project.whatsfordinner

import android.app.Application
import resume.project.whatsfordinner.data.AppDatabase

class WhatsForDinnerApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }
}