package example.grails

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

class ValidImage implements Validateable {
    MultipartFile imageFile
    Long id
    Integer version

    static constraints = {
        id nullable: false
        version nullable: false
        imageFile  validator: { val, obj ->
            if ( val == null ) {
                return false
            }
            if ( val.empty ) {
                return false
            }

            ['jpeg', 'jpg', 'png'].any { extension -> 
                 val.originalFilename?.toLowerCase()?.endsWith(extension)
            }
        }
    }
}