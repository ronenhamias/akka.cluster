package io.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	
	public static void main(String[] args) {
		
		final Logger log = LoggerFactory.getLogger(ClusterEventListener.class);
		
		AkkaCluster cluster1  = AkkaCluster.create("ClusterSystem","seed");
		AkkaCluster cluster2 = AkkaCluster.create("ClusterSystem","application");
		AkkaCluster cluster3 = AkkaCluster.create("ClusterSystem","application");
		AkkaCluster cluster4 = AkkaCluster.create("ClusterSystem","application");
		AkkaCluster cluster5 = AkkaCluster.create("ClusterSystem","application");

		
	}
}
