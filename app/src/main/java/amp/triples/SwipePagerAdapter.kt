package amp.triples

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SwipePagerAdapter : FragmentPagerAdapter {

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {}

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return WeatherFragment()
            }
            1 -> {
                return FineDustFragment()
            }
        }

        return WeatherFragment()
    }

    override fun getCount(): Int {
        //페이지 수 2개 (날씨, 미세먼지)
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}