package org.wso2.oc.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {

    private Map<String,Node> nodes;
    private String clusterId;
    private String clusterName;
    private String clusterVersion;
    private String domain;
    private List<Tenant> tenants;
    private String status;
    private List<ClusterCommand> commands;
    private static final Log log= LogFactory.getLog(Cluster.class);

    public Cluster(){
        nodes=new HashMap<String,Node>();
        commands=new ArrayList<ClusterCommand>();
    }

	public void updateClusterStatus(){

		Node[] nodes = getNodes().values().toArray(new Node[getNodes().size()]);

		boolean allNodesDown = false;

		for(Node node:nodes){
			node.updateNodeStatus();

			if(node.getStatus().equals(ServerConstants.NODE_DOWN))
				allNodesDown = true;
		}

		if(allNodesDown){
			setStatus(ServerConstants.CLUSTER_DOWN);
		}
		else
			setStatus(ServerConstants.CLUSTER_RUNNING);
	}

    public int getNumberOfNodes(){
        return nodes.size();
    }

    public int getNumberOfActiveNodes(){
        int numberOfActiveNodes=0;

	    for(Node n:nodes.values()){
		    if(n.getStatus()==ServerConstants.NODE_RUNNING)
			    numberOfActiveNodes++;
	    }

        return numberOfActiveNodes;
    }

    public Map<String,Node> getNodes() {
        return this.nodes;
    }

    public void addNewNode(String nodeId,Node node) {
        this.nodes.put(nodeId,node);
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterVersion() {
        return clusterVersion;
    }

    public void setClusterVersion(String clusterVersion) {
        this.clusterVersion = clusterVersion;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClusterCommand> getCommands() {
        return commands;
    }

	public void addCommand(String commandId){
		if(commands == null)
			commands = new ArrayList<ClusterCommand>();

		commands.add(new ClusterCommand(commandId));
	}
}
