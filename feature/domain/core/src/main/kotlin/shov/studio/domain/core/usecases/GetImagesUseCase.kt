package shov.studio.domain.core.usecases

import data.common.datas.MainPictureLargeResponse
import data.common.repositories.StandardRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val standardRepository: StandardRepository) {
    suspend operator fun invoke(type: String, id: Int) = standardRepository.images(type, id)
        .map { response ->
            listOf(response.mainPictureResponse.large) + response.pictures
                .map(MainPictureLargeResponse::large)
        }
}
