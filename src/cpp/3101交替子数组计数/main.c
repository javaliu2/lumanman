#include <stdio.h>

/* my solution*/
long long countAlternatingSubarrays_my(int* nums, int len) {
    // brute force
    long long res = 0, cnt;
    for (size_t i = 0; i < len; i++) {
        cnt = 1;
        for (size_t j = i + 1; j < len; j++) {
            if (nums[j] != nums[j - 1]) {
                cnt++;
            } else {
                break;
            }
        }
        res += cnt;
    }
    return res;
}
/* 灵神 */
long long countAlternatingSubarrays(int* nums, int len) {
	long long ans = 0, cnt = 0;
	for (size_t i = 0; i < len; i++) {
		if (i > 0 && nums[i] == nums[i - 1]) {
			cnt = 1;
		} else {
			cnt++;
		}
		ans += cnt;
	}	
	return ans;

}
int main(){
	int p[] = {0,1,1,1};
	int len = 4;
	long long res = countAlternatingSubarrays(p, len);
	printf("%lld\n", res);
	return 0;
}
