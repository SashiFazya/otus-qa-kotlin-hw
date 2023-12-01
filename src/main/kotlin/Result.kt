import kotlin.reflect.full.functions

fun main(){
    val steps= Steps()
    val activeTestRunner = ActiveTestRunner()
    activeTestRunner.runTest(steps){
        println("TEST")
    }
}

interface TestRunner {
    fun <T:Any> runTest(steps: T, test:() -> Unit )
}

class ActiveTestRunner: TestRunner {
    override fun <T:Any> runTest(steps: T,test: () -> Unit) {
        steps::class.functions.forEach{
            if (it.name.startsWith("before"))
                it.call(steps)
        }

        println("Test is running")

        test()

        steps::class.functions.forEach{
            if (it.name.startsWith("after"))
                it.call(steps)
        }

        println("Test is completed")
    }
}

class Steps(){
    fun beforeAllTestRun(){
        println("should be run before all tests")
    }

    fun beforeDBWrite() {
        println("should be run to get DB access")
    }

    fun bodyFun(){
        println("this method shouldn't be run")
    }

    fun afterEach(){
        println("should be run at the end")
    }
}

