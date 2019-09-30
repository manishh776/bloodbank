package com.bloodbank

import android.app.Application
import com.bloodbank.data.network.FirebaseApi
import com.bloodbank.data.repositories.UserRepository
import com.bloodbank.ui.auth.AuthViewModelFactory
import com.google.firebase.database.FirebaseDatabase
import net.simplifiedcoding.mvvmsampleapp.data.preferences.PreferenceProvider

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { FirebaseApi(FirebaseDatabase.getInstance().reference) }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance()) }
    }

}