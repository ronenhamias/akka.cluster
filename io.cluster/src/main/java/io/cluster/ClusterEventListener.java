package io.cluster;
 
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterEventListener extends UntypedActor {
  Logger log = LoggerFactory.getLogger(ClusterEventListener.class);
  Cluster cluster = Cluster.get(getContext().system());

  //subscribe to cluster changes
  @Override
  public void preStart() {
    //#subscribe
    cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
    //#subscribe
  }
 
  //re-subscribe when restart
  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
  }
 
  @Override
  public void onReceive(Object message) {
    if (message instanceof MemberUp) {
      MemberUp mUp = (MemberUp) message;
      
      log.info(cluster.selfAddress() + " Member is Up: {}", mUp.member());     
      log.info(cluster.selfAddress() + " current cluster state {}", cluster.state().members());
      
    } else if (message instanceof UnreachableMember) {
      UnreachableMember mUnreachable = (UnreachableMember) message;
     
      log.info(cluster.selfAddress() + " Member detected as unreachable: {}", mUnreachable.member());
      log.info(cluster.selfAddress() + " current cluster state {}", cluster.state().members());
      
    } else if (message instanceof MemberRemoved) {
      MemberRemoved mRemoved = (MemberRemoved) message;
      log.info(cluster.selfAddress() + " Member is Removed: {}", mRemoved.member());
      log.info(cluster.selfAddress() + " current cluster state {}", cluster.state().members());
      
    } else if (message instanceof MemberEvent) {
      // ignore
    } else {
      unhandled(message);
    }
  }
}