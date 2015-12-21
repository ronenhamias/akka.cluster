package io.cluster;

import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.ClusterDomainEvent;
import akka.cluster.Member;

public class AkkaCluster {

	private final ActorSystem system;
	ActorRef clusterListener; 
	private AkkaCluster(String name, String conf){
		Config config1 = ConfigFactory.load(conf);
		system = ActorSystem.create(name,config1);
		// Create an actor that handles cluster domain events
	    clusterListener = system.actorOf( Props.create ( ClusterEventListener.class), "clusterListener");
	    // Add subscription of cluster events
	    Cluster.get(system).subscribe(clusterListener, ClusterDomainEvent.class);
	    
	}
	
	public void register(IClusterListener listener){
		
	}
	
	public static AkkaCluster create(String name, String conf){
		return new AkkaCluster(name, conf);	    
	}
	
	void join(List<Address> seedNodes){
		Cluster.get(system).joinSeedNodes(seedNodes);
	}
	
	void leave(Address address){
		Cluster.get(system).leave(address);
	}
	
	public Address selfAddress(){
		return Cluster.get(system).selfAddress();
	}

	public Iterable<Member> members() {
		return Cluster.get(system).state().getMembers();	
	}
	
}
