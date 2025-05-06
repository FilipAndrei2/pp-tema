import chain.Handler
import chain.CEOHandler
import chain.ExecutiveHandler
import chain.HappyWorkerHandler
import chain.ManagerHandler
import factory.FactoryProducer

fun main()
{
    val factoryProducer = FactoryProducer()

    val eliteFactory = factoryProducer.getFactory("Elite")
    val happyWorkerFactory = factoryProducer.getFactory("HappyWorker")

    val ceoHandler1 = eliteFactory.getHandler("CEO")
    val ceoHandler2 = eliteFactory.getHandler("CEO")
    val executiveHandler1 = eliteFactory.getHandler("Executive")
    val executiveHandler2 = eliteFactory.getHandler("Executive")
    val managerHandler1 = eliteFactory.getHandler("Manager")
    val managerHandler2 = eliteFactory.getHandler("Manager")
    val happyWorkerHandler1 = happyWorkerFactory.getHandler("HappyWorker")
    val happyWorkerHandler2 = happyWorkerFactory.getHandler("HappyWorker")

    ceoHandler1.next1 = executiveHandler2
    ceoHandler1.next2 = ceoHandler2
    executiveHandler1.next1 = managerHandler2
    executiveHandler1.next2 = executiveHandler2
    managerHandler1.next1 = happyWorkerHandler1
    managerHandler1.next2 = managerHandler2
    happyWorkerHandler1.next2 = happyWorkerHandler2

    ceoHandler2.next1 = executiveHandler2
    ceoHandler2.next2 = ceoHandler1
    executiveHandler2.next1 = managerHandler2
    executiveHandler2.next2 = executiveHandler1
    managerHandler2.next1 = happyWorkerHandler2
    managerHandler2.next2 = managerHandler1
    happyWorkerHandler2.next2 = happyWorkerHandler1

    ceoHandler1.handleRequest("Right", "3:mesaj")
    println()
    executiveHandler1.handleRequest("Right", "2:mesaj")
    println()
    managerHandler1.handleRequest("Right", "4:mesaj")
    println()
    happyWorkerHandler1.handleRequest("Right", "4:mesaj")
    println()
    managerHandler2.handleRequest("Right", "3:mesaj")
}