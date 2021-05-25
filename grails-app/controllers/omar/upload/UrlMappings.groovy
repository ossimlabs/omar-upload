package omar.upload

class UrlMappings {

    static mappings = {

        "/" {
            controller = "upload"
            action = "index"
        } 

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
