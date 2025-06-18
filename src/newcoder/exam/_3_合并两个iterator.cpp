#include <algorithm>
#include <iostream>
using namespace std;

// 64 位输出请用 printf("%lld")

struct Iterator {
    virtual bool hasNext() = 0;
    virtual int next() = 0;
};
class MockIterator : public Iterator {
    int m_current, m_step, m_endValue;
  public:
    MockIterator(int step, int endValue) {
        m_step = step;
        m_endValue = endValue;
        m_current = endValue % step;
    }
    bool hasNext() {
        return m_current < m_endValue;
    }
    // 这里很膈应
    int next() {
        return m_current += m_step;
    }
};
class MergeIterator : public Iterator {
  private:
    MockIterator* ita;
    MockIterator* itb;
    bool has_a, has_b;
    int a, b;

  public:
    MergeIterator(MockIterator* ita, MockIterator* itb)
        : ita(ita), itb(itb), has_a(false), has_b(false) {}

    bool hasNext() override {
        return has_a || has_b || ita->hasNext() || itb->hasNext();
    }

    int next() override {
        if (!has_a && ita->hasNext()) {
            a = ita->next();
            has_a = true;
        }
        if (!has_b && itb->hasNext()) {
            b = itb->next();
            has_b = true;
        }

        if (has_a && has_b) {
            if (a <= b) {
                has_a = false;
                return a;
            } else {
                has_b = false;
                return b;
            }
        }

        if (has_a) {
            has_a = false;
            return a;
        }
        if (has_b) {
            has_b = false;
            return b;
        }

        return -1; // 理论上不应该到这里
    }
};

int main() {
    int ita_1, ita_2, itb_1, itb_2;
    cin >> ita_1 >> ita_2;
    cin >> itb_1 >> itb_2;
    MockIterator ita(ita_1, ita_2);
    MockIterator itb(itb_1, itb_2);
    MergeIterator it(&ita, &itb);
    while (it.hasNext()) {
        cout << it.next();
        if (it.hasNext()) {
            cout << " ";
        }
    }
    return 0;
}