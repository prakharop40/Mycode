#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

const int MIN_MERGE = 32;

int minRunLength(int n) {
    int r = 0;
    while (n >= MIN_MERGE) {
        r |= (n & 1);
        n >>= 1;
    }
    return n + r;
}

void insertionSort(int arr[], int left, int right) {
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

void merger(int arr[], int l, int m, int r) {
    int len1 = m - l + 1, len2 = r - m;
    vector<int> left(len1);
    vector<int> right(len2);
    
    for (int x = 0; x < len1; x++)
        left[x] = arr[l + x];
    for (int x = 0; x < len2; x++)
        right[x] = arr[m + 1 + x];
    
    int i = 0, j = 0, k = l;
    
    while (i < len1 && j < len2) {
        if (left[i] <= right[j]) {
            arr[k] = left[i];
            i++;
        } else {
            arr[k] = right[j];
            j++;
        }
        k++;
    }
    
    while (i < len1) {
        arr[k] = left[i];
        k++;
        i++;
    }
    
    while (j < len2) {
        arr[k] = right[j];
        k++;
        j++;
    }
}

void timSort(int arr[], int n) {
    int minRun = minRunLength(MIN_MERGE);
    
    for (int i = 0; i < n; i += minRun) {
        insertionSort(arr, i, min(i + MIN_MERGE - 1, n - 1));
    }
    
    for (int size = minRun; size < n; size = 2 * size) {
        for (int left = 0; left < n; left += 2 * size) {
            int mid = left + size - 1;
            int right = min(left + 2 * size - 1, n - 1);
            if (mid < right)
                merger(arr, left, mid, right);
        }
    }
}
void heapify(int arr[], int N, int i) {
    int largest = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;
    
    if (l < N && arr[l] > arr[largest])
        largest = l;
    
    if (r < N && arr[r] > arr[largest])
        largest = r;
    
    if (largest != i) {
        int swap = arr[i];
        arr[i] = arr[largest];
        arr[largest] = swap;
        heapify(arr, N, largest);
    }
}

void heapsort(int arr[], int N) {
    for (int i = N / 2 - 1; i >= 0; i--)
        heapify(arr, N, i);
    
    for (int i = N - 1; i > 0; i--) {
        int temp = arr[0];
        arr[0] = arr[i];
        arr[i] = temp;
        heapify(arr, i, 0);
    }
}


int getMax(int arr[], int n) {
    int mx = arr[0];
    for (int i = 1; i < n; i++)
        if (arr[i] > mx)
            mx = arr[i];
    return mx;
}

void countSort(int arr[], int n, int exp) {
    int output[n];
    int count[10] = {0};
    
    for (int i = 0; i < n; i++)
        count[(arr[i] / exp) % 10]++;
    
    for (int i = 1; i < 10; i++)
        count[i] += count[i - 1];
    
    for (int i = n - 1; i >= 0; i--) {
        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
        count[(arr[i] / exp) % 10]--;
    }
    
    for (int i = 0; i < n; i++)
        arr[i] = output[i];
}

void radixsort(int arr[], int n) {
    int m = getMax(arr, n);
    for (int exp = 1; m / exp > 0; exp *= 10)
        countSort(arr, n, exp);
}

void merge(int arr[], int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;
    vector<int> L(n1);
    vector<int> R(n2);
    
    for (int i = 0; i < n1; ++i)
        L[i] = arr[l + i];
    for (int j = 0; j < n2; ++j)
        R[j] = arr[m + 1 + j];
    
    int i = 0, j = 0, k = l;
    
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }
    
    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }
    
    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void sort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        sort(arr, l, m);
        sort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}

void insertionSort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int current = arr[i];
        int j = i - 1;
        while (j >= 0 && current < arr[j]) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = current;
    }
}

void selectionSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        int small = arr[i];
        int index = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < small) {
                small = arr[j];
                index = j;
            }
        }
        int temp = arr[i];
        arr[i] = arr[index];
        arr[index] = temp;
    }
}

void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 1; j < n - i; j++) {
            if (arr[j - 1] > arr[j]) {
                int temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
            }
        }
    }
}

int getNextGap(int gap) {
    gap = (gap * 10) / 13;
    if (gap < 1)
        return 1;
    return gap;
}

void combSort(int arr[], int n) {
    int gap = n;
    bool swapped = true;
    
    while (gap != 1 || swapped == true) {
        gap = getNextGap(gap);
        swapped = false;
        
        for (int i = 0; i < n - gap; i++) {
            if (arr[i] > arr[i + gap]) {
                int temp = arr[i];
                arr[i] = arr[i + gap];
                arr[i + gap] = temp;
                swapped = true;
            }
        }
    }
}

void flip(int arr[], int i) {
    int temp, start = 0;
    while (start < i) {
        temp = arr[start];
        arr[start] = arr[i];
        arr[i] = temp;
        start++;
        i--;
    }
}

int findMax(int arr[], int n) {
    int mi, i;
    for (mi = 0, i = 0; i < n; ++i)
        if (arr[i] > arr[mi])
            mi = i;
    return mi;
}

void pancakeSort(int arr[], int n) {
    for (int curr_size = n; curr_size > 1; --curr_size) {
        int mi = findMax(arr, curr_size);
        if (mi != curr_size - 1) {
            flip(arr, mi);
            flip(arr, curr_size - 1);
        }
    }
}

int main() {
    cout << "Hi, I am a sorting AI that can sort a given number of elements in ascending order using various sorting algorithms." << endl;
    cout << "Enter the number of values you want me to sort: ";
    int size;
    cin >> size;
    int arr[size];
    cout << "Enter the values: ";
    for (int i = 0; i < size; i++) {
        cin >> arr[i];
    }
    cout << "The given values before sorting are: ";
    for (int i = 0; i < size; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;
    cout << "Choose how you want me to sort the values:" << endl;
    cout << "1. By using bubble sort." << endl;
    cout << "2. By using selection sort." << endl;
    cout << "3. By using insertion sort." << endl;
    cout << "4. By using merge sort." << endl;
    cout << "5. By using radix sort." << endl;
    cout << "6. By using comb sort." << endl;
    cout << "7. By using pancake sort." << endl;
    cout << "8. By using heap sort." << endl;
    cout << "9. By using tim sort." << endl;
    cout << "Enter your choice: ";
    int c;
    cin >> c;
    
    switch (c) {
        case 1:
            bubbleSort(arr, size);
            break;
        case 2:
            selectionSort(arr, size);
            break;
        case 3:
            insertionSort(arr, size);
            break;
        case 4:
            sort(arr, 0, size - 1);
            break;
        case 5:
            radixsort(arr, size);
            break;
        case 6:
            combSort(arr, size);
            break;
        case 7:
            pancakeSort(arr, size);
            break;
        case 8:
            heapsort(arr, size);
            break;
        case 9:
            timSort(arr, size);
            break;
        default:
            cout << "Wrong input!" << endl;
            return 0;
    }
    
    cout << "The given values after sorting are: ";
    for (int i = 0; i < size; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;
    
    return 0;
}

