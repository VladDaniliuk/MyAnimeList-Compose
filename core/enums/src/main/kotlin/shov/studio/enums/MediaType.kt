package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class MediaType(@StringRes val id: Int) {
    unknown(R.string.unknown), tv(R.string.tv), ova(R.string.ova), movie(R.string.movie),
    special(R.string.special), ona(R.string.ona), music(R.string.music), manga(R.string.manga),
    novel(R.string.novel), one_shot(R.string.one_shot), doujinshi(R.string.doujinshi),
    manhwa(R.string.manhwa), manhua(R.string.manhua), oel(R.string.oel),
    light_novel(R.string.light_novel), pv(R.string.pv)
}
