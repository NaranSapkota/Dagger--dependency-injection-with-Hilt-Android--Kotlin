package com.example.dagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //field injection
    @Inject
    lateinit var someClass: SomeClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}

// constructor injection
class SomeClass
@Inject
constructor(
    @Impl1 private val someInterfaceImpl1: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface,
//    private val gson: Gson
) {
    fun doAThing1(): String {
        return "Look I got: ${someInterfaceImpl1.getAThing()}"
    }

    fun doAThing2(): String {
        return "Look I got: ${someInterfaceImpl2.getAThing()}"
    }
}


//for INTERFACE
class SomeInterfaceImpl1
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return " A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return " A Thing2"
    }
}


interface SomeInterface {
    fun getAThing(): String
}

@InstallIn(SingletonComponent::class)  //Application Component is  deprecited  in dagger version 2.30 with SingletonComponent
@Module
class MyModule {

    @Impl1
    @Singleton
    @Provides
    fun providesSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun providesSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2


//This is for Provides

//@InstallIn(SingletonComponent::class)
//@Module
//  class MyModuleProvider{
//
//    @Singleton
//    @Provides
//    fun getSomeString():String{
//        return "Some String"
//    }
//
//    @Singleton
//    @Provides
//    fun  provideSomeInterface(
//        someString: String
//    ):SomeInterface{
//        return SomeInterfaceImpl()
//    }
//}
//

//This is for Bind
//@InstallIn(SingletonComponent::class)
//@Module
//abstract  class MyModule{
//
//    @ActivityScoped     // Bind and Provides for interface in dagger hilt
//    @Binds
//    abstract fun  bindSomeDependency(
//        someInterfaceImpl: SomeInterfaceImpl
//    ):SomeInterface
//}

// Android class        Generated component                Scope
//Application           ApplicationComponent`              @Singleton
//View Model            ActivityRetainedComponent          @ActivityRetainedScope
//Activity              ActivityComponent                  @ActivityScope
//Fragment              FragmentComponent                  @FragmentScoped
//View                  V iewComponent                      @ViewScoped
//View annoted with     ViewWithFragmentComponent          @ViewScoped
// @WithFragmentBindings