import java.lang.reflect.Array;
import java.util.ArrayList;

public class Merge {
    public static ArrayList<Arc> makeNeighbourGraph(ArrayList<Cube> cubelist){
        //list of arcs representing the graph
        ArrayList<Arc> arcs = new ArrayList<>();
        //for each cube, we test if the other cubes are its neighbour
        for(int firstcube=0; firstcube< cubelist.size()-1; firstcube++){
            for(int secondcube=firstcube+1; secondcube< cubelist.size(); secondcube++){
                //if they are neighbours, we create an arc that we add to the graph
                if(cubelist.get(firstcube).isNeighbourWith(cubelist.get(secondcube))){
                    Arc arc = new Arc(firstcube, secondcube);
                    arcs.add(arc);
                }
            }
        }
        return arcs;
    }

    public static void changeMinMax(ArrayList<Group> groups, int keptgroup, int modifiedgroup){
        groups.get(keptgroup).setColormin(Math.min(groups.get(modifiedgroup).getColormin(), groups.get(keptgroup).getColormin()));
        groups.get(keptgroup).setColormax(Math.max(groups.get(modifiedgroup).getColormax(), groups.get(keptgroup).getColormax()));
    }

    public static Boolean homogeneityTest(Group group1,Group group2, float homogeneityC){
        float maxcolor = Math.max(group1.getColormax(),group2.getColormax());
        float mincolor = Math.min(group1.getColormin(),group2.getColormin());
        return (maxcolor - mincolor) < homogeneityC;
    }

    public static void merge2Groups(ArrayList<Group> groups, int firstGroup, int secondGroup, int[] associatedgroups){
        //newgroup and modifiedgroup represent the groups associated to firstgroup and secondgroup
        int keptgroup = associatedgroups[firstGroup];
        int modifiedgroup = associatedgroups[secondGroup];
        //we change the highest associated group into the lowest one
        if (modifiedgroup<keptgroup){
            modifiedgroup=associatedgroups[firstGroup];
            keptgroup=associatedgroups[secondGroup];
        }
        for(int i=0; i<associatedgroups.length; i++){
            if(associatedgroups[i]==modifiedgroup) associatedgroups[i]=keptgroup;
        }
        //we update the minimum and maximum of the groups and remove a group to the total number of groups
        changeMinMax(groups, keptgroup, modifiedgroup);
        Group.lessNbofGroup();
    }

    public static int[] merge(float homogeneityC, ArrayList<Arc> arcs, ArrayList<Group> groups){
        //lengths of the two lists
        int arcsize = arcs.size();
        int groupsize = groups.size();
        //variables used later to represent the origin and the end of an arc
        int arcorigin;
        int arcend;
        //each group is at first associated with itself
        int[] associatedgroups = new int[groupsize];
        for(int i=0; i<groupsize; i++){
            associatedgroups[i]=i;
        }
        //for each arc
        for(int i=0; i<arcsize; i++){
            arcorigin = arcs.get(i).getStart();
            arcend = arcs.get(i).getEnd();
            //if the origin and the end are not associated
            if(associatedgroups[arcorigin]!=associatedgroups[arcend]){
                //if the two groups are homogeneous they are merged and associatedgroups is updated
                if(homogeneityTest(groups.get(associatedgroups[arcorigin]),groups.get(associatedgroups[arcend]), homogeneityC)){
                    merge2Groups(groups, associatedgroups[arcorigin], associatedgroups[arcend], associatedgroups);
                }
            }
        }
        return associatedgroups;
    }
}
