package practice;

import java.util.*;
import java.io.*;
public class sorting
{
    static int MIN_MERGE = 32;

    public static int minRunLength(int n)
    {
        assert n >= 0;

        // Becomes 1 if any 1 bits are shifted off
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    // This function sorts array from left index to
    // to right index which is of size atmost RUN
    public static void insertionSort(int[] arr, int left,
                                     int right)
    {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    // Merge function merges the sorted runs
    public static void merger(int[] arr, int l, int m, int r)
    {
        // Original array is broken in two parts
        // left and right array
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++) {
            left[x] = arr[l + x];
        }
        for (int x = 0; x < len2; x++) {
            right[x] = arr[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // After comparing, we merge those two array
        // in larger sub array
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            }
            else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        // of left, if any
        while (i < len1) {
            arr[k] = left[i];
            k++;
            i++;
        }

        // Copy remaining element
        // of right, if any
        while (j < len2) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    // Iterative Timsort function to sort the
    // array[0...n-1] (similar to merge sort)
    public static void timSort(int[] arr, int n)
    {
        int minRun = minRunLength(MIN_MERGE);

        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += minRun) {
            insertionSort(
                arr, i,
                Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        // Start merging from size
        // RUN (or 32). It will
        // merge to form size 64,
        // then 128, 256 and so on
        // ....
        for (int size = minRun; size < n; size = 2 * size) {

            // Pick starting point
            // of left sub array. We
            // are going to merge
            // arr[left..left+size-1]
            // and arr[left+size, left+2*size-1]
            // After every merge, we
            // increase left by 2*size
            for (int left = 0; left < n; left += 2 * size) {

                // Find ending point of left sub array
                // mid+1 is starting point of right sub
                // array
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1),
                                     (n - 1));

                // Merge sub array arr[left.....mid] &
                // arr[mid+1....right]
                if (mid < right)
                    merger(arr, left, mid, right);
            }
        }
    }
    static void heapsort(int arr[])
    {
        int N = arr.length;
 
        // Build heap (rearrange array)
        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(arr, N, i);
 
        // One by one extract an element from heap
        for (int i = N - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
 
    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    static void heapify(int arr[], int N, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
 
        // If left child is larger than root
        if (l < N && arr[l] > arr[largest])
            largest = l;
 
        // If right child is larger than largest so far
        if (r < N && arr[r] > arr[largest])
            largest = r;
 
        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
 
            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }
    static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // The main function to that sorts arr[] of
    // size n using Radix Sort
    static void radixsort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }
    static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        // Merge the temp arrays
 
        // Initial indices of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    static void sort(int arr[], int l, int r)
    {
        if (l < r) {
 
            // Find the middle point
            int m = l + (r - l) / 2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
 
    static void insertionSort(int arr[]){
        for(int i=1;i<arr.length;i++){
            int current = arr[i];
            int j = i-1;
            while(j >= 0 && current < arr[j]){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = current;
        }
    }
        static void selectionSort(int arr[]){
        int small,i,j,temp,index;
        for(i = 0;i<arr.length-1;i++){
            small = arr[i];
            index = i;
            for(j=i+1;j<arr.length;j++){
                if(arr[j]<small){
                    small = arr[j];
                    index = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
    }
        static void bubbleSort(int[] arr) {  
        int n = arr.length;  
        int temp = 0;  
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(arr[j-1] > arr[j]){  
                                 //swap elements  
                                 temp = arr[j-1];  
                                 arr[j-1] = arr[j];  
                                 arr[j] = temp;  
                         }  
                          
                 }  
         }  
  
    }
    static int getNextGap(int gap)
    {
        // Shrink gap by Shrink factor
        gap = (gap*10)/13;
        if (gap < 1)
            return 1;
        return gap;
    }
 
    // Function to sort arr[] using Comb Sort
    static void sort(int arr[])
    {
        int n = arr.length;
 
        // initialize gap
        int gap = n;
 
        // Initialize swapped as true to make sure that
        // loop runs
        boolean swapped = true;
 
        // Keep running while gap is more than 1 and last
        // iteration caused a swap
        while (gap != 1 || swapped == true)
        {
            // Find next gap
            gap = getNextGap(gap);
 
            // Initialize swapped as false so that we can
            // check if swap happened or not
            swapped = false;
 
            // Compare all elements with current gap
            for (int i=0; i<n-gap; i++)
            {
                if (arr[i] > arr[i+gap])
                {
                    // Swap arr[i] and arr[i+gap]
                    int temp = arr[i];
                    arr[i] = arr[i+gap];
                    arr[i+gap] = temp;
 
                    // Set swapped
                    swapped = true;
                }
            }
        }
    }
     /* Reverses arr[0..i] */
     static void flip(int arr[], int i)
     {
         int temp, start = 0;
         while (start < i)
         {
             temp = arr[start];
             arr[start] = arr[i];
             arr[i] = temp;
             start++;
             i--;
         }
     }
  
     // Returns index of the
     // maximum element in
     // arr[0..n-1]
     static int findMax(int arr[], int n)
     {
         int mi, i;
         for (mi = 0, i = 0; i < n; ++i)
             if (arr[i] > arr[mi])
                 mi = i;
         return mi;
     }
  
     // The main function that
     // sorts given array using
     // flip operations
     static int pancakeSort(int arr[], int n)
     {
         // Start from the complete
         // array and one by one
         // reduce current size by one
         for (int curr_size = n; curr_size > 1;
                                  --curr_size)
         {
             // Find index of the
             // maximum element in
             // arr[0..curr_size-1]
             int mi = findMax(arr, curr_size);
  
             // Move the maximum element
             // to end of current array
             // if it's not already at
             // the end
             if (mi != curr_size-1)
             {
                 // To move at the end,
                 // first move maximum
                 // number to beginning
                 flip(arr, mi);
  
                 // Now move the maximum
                 // number to end by
                 // reversing current array
                 flip(arr, curr_size-1);
             }
         }
         return 0;
     }
 
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hi, i am a sorting AI that can sort/search a given no. of element(s) in ascending order by using most of the famous sorting algorithms.");
        System.out.println("Enter the no. of values you want me to sort: ");
        int size = sc.nextInt();
        int arr[] = new int[size];
        System.out.println("Enter the values: ");
        for(int i=0;i<=size-1;i++){
            arr[i] = sc.nextInt();
        }
        System.out.println("The given values before sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"  ");
        }
        System.out.println();
        System.out.println("Choose how you want me to sort/search the values: ");
        System.out.println("1.By using bubble sort.");
        System.out.println("2.By using selection sort. ");
        System.out.println("3. By using insertion sort.");
        System.out.println("4. using merge sort.");
        System.out.println("5. using radix sort.");
        System.out.println("6. by using comb sort.");
        System.out.println("7. by using pancake sort.");
        System.out.println("8. using heap sort.");
        System.out.println("9. by using tim sort.");
       // System.out.println("3. you want to search in the array.");
        System.out.println("Enter your choice: ");
        int c = sc.nextInt();
        if(c==1){
            bubbleSort(arr);
             System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
        }
        else if(c==2){
            selectionSort(arr);
             System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
        }
        else if(c==3){
        insertionSort(arr);
         System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==4){
        sort(arr, 0, arr.length-1);
        System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==5){
        radixsort(arr, arr.length);
        System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==6){
        sort(arr);
         System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==7){
        pancakeSort(arr, arr.length);
         System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==8){
        heapsort(arr);
         System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    else if(c==9){
        timSort(arr, arr.length);
          System.out.println("The given values after sorting are: ");
        for(int i=0;i<=arr.length-1;i++){
            System.out.print(arr[i]+"    ");
        }
    }
    
        else{
            System.out.println("wrong input!");
            System.exit(0);
        }
        System.out.println();
       
    }
}