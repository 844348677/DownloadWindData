package tdb.test;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.collection.mutable.LinkedList;
import tdb.test.actor.TestActor;
import tdb.test.actor.TestActor2;

public class TestAkka {
	public static void main(String[] args) {
		ActorSystem _system = ActorSystem.create("TestAkka");
		ActorRef akkaActor1 = _system.actorOf(new Props(TestActor.class), "akkatest1");
		ActorRef akkaActor2 = _system.actorOf(new Props(TestActor.class), "akkatest2");
		ActorRef akkaActor3 = _system.actorOf(new Props(TestActor.class), "akkatest3");
		ActorRef akkaActor4 = _system.actorOf(new Props(TestActor.class), "akkatest4");
		ActorRef akkaActor5 = _system.actorOf(new Props(TestActor.class), "akkatest5");
		ActorRef akkaActor6 = _system.actorOf(new Props(TestActor.class), "akkatest6");
		
/*		akkaActor1.tell(11);
		akkaActor2.tell(12);
		akkaActor3.tell(13);
		akkaActor4.tell(14);
		akkaActor5.tell(15);
		akkaActor6.tell(16);*/
		
		//Thread.sleep(100);
		
		List<Integer> intList =  new java.util.LinkedList<Integer>();
		
		for(int i=0;i<4000;i++){
			intList.add(i+1);
		}
		
		for(int i=0;i<500;i++){
			akkaActor1.tell(intList.get(i));
		}
		for(int i=500;i<1000;i++){
			akkaActor2.tell(intList.get(i));
		}
		for(int i=1000;i<1500;i++){
			akkaActor3.tell(intList.get(i));
		}
		for(int i=2000;i<2500;i++){
			akkaActor4.tell(intList.get(i));
		}
		for(int i=2500;i<3000;i++){
			akkaActor5.tell(intList.get(i));
		}
		
	}
}
