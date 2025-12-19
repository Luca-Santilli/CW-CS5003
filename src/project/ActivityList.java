package project;

import java.util.LinkedList;

public class ActivityList extends LinkedList<Activity> {
    
    private final int MAX_SIZE = 4;
   
    @Override
    public void addLast(Activity activity) {
        super.addLast(activity);
        
        if (size() > MAX_SIZE) {
            removeFirst();
        }
    }
    
    public ActivityList getSorted() {
        
        ActivityList sortedList = new ActivityList();
        sortedList.addAll(this);
        
        int size = sortedList.size();

        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            
            for (int j = i + 1; j < size; j++) {
                if (sortedList.get(j).getQuantity() < sortedList.get(minIndex).getQuantity()) {
                    minIndex = j;
                }
            }

            Activity tempActivity = sortedList.get(minIndex);
            sortedList.set(minIndex, sortedList.get(i));
            sortedList.set(i, tempActivity);
        }
        
        return sortedList;
    }

}
