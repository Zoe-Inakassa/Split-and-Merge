package Main;

import java.util.ArrayList;

/**
 * Class containing methods to merge an image
 */
public class Merge {

    /**
     * Change the min and max of the modified group
     * Colormin will take the value of the lowest colormin values of the two groups in the parameters
     * Colormax will take the value of the highest colormax values of the two groups in the parameters
     * @param groups ArrayList<Group>, list of groups
     * @param keptgroup int, number of the group to keep the same
     * @param modifiedgroup int, number of the group to modify
     */
    private static void changeMinMax(ArrayList<Group> groups, int keptgroup, int modifiedgroup){
        groups.get(keptgroup).setColormin(Math.min(groups.get(modifiedgroup).getColormin(), groups.get(keptgroup).getColormin()));
        groups.get(keptgroup).setColormax(Math.max(groups.get(modifiedgroup).getColormax(), groups.get(keptgroup).getColormax()));
    }

    /**
     * Test if two groups are homogeneous
     * @param group1 int, number of the first group
     * @param group2 int, number of the second group
     * @param homogeneityC float, homogeneity criteria
     * @return boolean, true if the two groups are homogeneous, false if they are not
     */
    private static Boolean homogeneityTest(Group group1,Group group2, float homogeneityC){
        float maxcolor = Math.max(group1.getColormax(),group2.getColormax());
        float mincolor = Math.min(group1.getColormin(),group2.getColormin());
        return (maxcolor - mincolor) < homogeneityC;
    }

    /**
     * Merge 2 groups together by changing their associated groups in the associatedgroups array
     * Will change all the groups associated to the higher one into the lower one
     * @param groups ArrayList<Group>, list of groups
     * @param firstGroup int, number of the first group
     * @param secondGroup int, number of the second group
     * @param associatedgroups, int[] array of the associated groups of the cubes
     */
    private static void merge2Groups(ArrayList<Group> groups, int firstGroup, int secondGroup, int[] associatedgroups){
        //keptgroup and modifiedgroup represent the groups associated to firstgroup and secondgroup
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

    /**
     * Operation merge on the image as a whole
     * Tests for each arc that the associated groups of the origin and end cubes are different
     * Tests if they are homogeneous
     * Merge the groups together
     * @param homogeneityC float, homogeneity criteria
     * @param arcs ArrayList(Arc), list of arcs representing the graph
     * @param groups ArrayList(Group), list of groups
     * @return associatedgroups, int[] an array containing the groups associated with each cube after the merge
     */
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
