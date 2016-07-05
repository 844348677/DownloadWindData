package tdb.test;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.collection.mutable.LinkedList;
import tdb.test.actor.TestActor;
import tdb.test.actor.TestActor2;

public class TestAkka2 {
	public static void main(String[] args) {

		ActorSystem _system = ActorSystem.create("TestAkka");
		ActorRef akkaActor1 = _system.actorOf(new Props(TestActor2.class), "akkatest1");
		ActorRef akkaActor2 = _system.actorOf(new Props(TestActor2.class), "akkatest2");
		ActorRef akkaActor3 = _system.actorOf(new Props(TestActor2.class), "akkatest3");
		ActorRef akkaActor4 = _system.actorOf(new Props(TestActor2.class), "akkatest4");
		ActorRef akkaActor5 = _system.actorOf(new Props(TestActor2.class), "akkatest5");
		ActorRef akkaActor6 = _system.actorOf(new Props(TestActor2.class), "akkatest6");
		
		List<String> sendList1 = new java.util.LinkedList<String>();
		List<String> sendList2 = new java.util.LinkedList<String>();
		List<String> sendList3 = new java.util.LinkedList<String>();
		List<String> sendList4 = new java.util.LinkedList<String>();
		List<String> sendList5 = new java.util.LinkedList<String>();
		
		for(int i=0;i<500;i++){
			sendList1.add((10000+i)+"");
			sendList2.add((20000+i)+"");
			sendList3.add((30000+i)+"");
			sendList4.add((40000+i)+"");
			sendList5.add((50000+i)+"");
		}
		
		akkaActor1.tell(sendList1);
		akkaActor2.tell(sendList2);
		akkaActor3.tell(sendList3);
		akkaActor4.tell(sendList4);
		akkaActor5.tell(sendList5);
		
	}
}
