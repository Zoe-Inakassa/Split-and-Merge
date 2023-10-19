import java.lang.reflect.Array;
import java.util.ArrayList;

public class Merge {
    public static ArrayList<Arc> makeNeighbourGraph(ArrayList<Cube> cubelist){
        //list of arcs representing the graph
        ArrayList<Arc> arcs = new ArrayList<Arc>();
        //for each cube, we test if the other cubes are its neighbour
        for(int firstcube=0; firstcube< cubelist.size()-1; firstcube++){
            for(int secondcube=firstcube+1; secondcube< cubelist.size(); secondcube++){
                //if they are neighbours, we create an arc that we add to the graph
                if(cubelist.get(firstcube).IsNeighbourWith(cubelist.get(secondcube))){
                    Arc arc = new Arc(firstcube, secondcube);
                    arcs.add(arc);
                }
            }
        }
        return arcs;
    }

    public static void changeMinMax(ArrayList<Group> groups, int newgroup, int modifiedgroup){
        //modified and new colors contain the min or max color of groups
        float modifiedcolor = groups.get(modifiedgroup).getColormin();
        float newcolor = groups.get(newgroup).getColormin();
        //if modified color min is lower than the new color min newcolor changes its min
        if(modifiedcolor<newcolor){
            groups.get(newgroup).setColormin(modifiedcolor);
        }
        modifiedcolor = groups.get(modifiedgroup).getColormax();
        newcolor = groups.get(newgroup).getColormax();
        //if modified color max is higher than the new color min newcolor changes its max
        if(modifiedcolor>newcolor){
            groups.get(newgroup).setColormax(modifiedcolor);
        }
    }

    public static void merge2Groups(int[] associatedgroups, int firstGroup, int secondGroup, ArrayList<Group> groups){
        //newgroup and modifiedgroup represent the groups associated to firstgroup and secondgroup
        int newgroup = associatedgroups[firstGroup];
        int modifiedgroup = associatedgroups[secondGroup];
        //we change the highest associated group into the lowest one
        if (modifiedgroup<newgroup){
            modifiedgroup=associatedgroups[firstGroup];
            newgroup=associatedgroups[secondGroup];
        }
        for(int i=0; i<associatedgroups.length; i++){
            if(associatedgroups[i]==modifiedgroup) associatedgroups[i]=newgroup;
        }
        //we update the minimum and maximum of the groups and remove a group to the total number of groups
        changeMinMax(groups, newgroup, modifiedgroup);
        Group.lessNbofGroup();
    }

    public static int[] merge(int homogeneityC, ArrayList<Arc> arcs, ArrayList<Group> groups){
        //lengths of the two lists
        int listsize = arcs.size();
        int tabsize = groups.size();
        //variables used later to represent the origin and the end of an arc
        int arcorigin;
        int arcend;
        //each group is at first associated with itself
        int[] associatedgroups = new int[tabsize];
        for(int i=0; i<tabsize; i++){
            associatedgroups[i]=i;
        }
        //for each arc
        for(int i=0; i<listsize-1; i++){
            arcorigin = arcs.get(i).getStart();
            arcend = arcs.get(i).getEnd();
            //if the origin and the end are not associated
            if(associatedgroups[arcorigin]!=associatedgroups[arcend]){
                //if the two groups are homogeneous they are merged and associatedgroups is updated
                if(Split.homogeneityTest(groups.get(associatedgroups[arcorigin]),groups.get(associatedgroups[arcend]), homogeneityC)){
                    merge2Groups(associatedgroups, associatedgroups[arcorigin], associatedgroups[arcend], groups);
                }
            }
        }
        return associatedgroups;
    }
}
