package com.metcs.cartservice.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component


@Component
class ExceptionUtil {
    fun getFullUri(request: HttpServletRequest): String {
        val scheme = request.scheme
        val serverName = request.serverName
        val serverPort = request.serverPort
        val contextPath = request.contextPath
        val servletPath = request.servletPath
        val pathInfo = request.pathInfo
        val queryString = request.queryString

        val builder = StringBuilder()
        builder.append(scheme).append("://").append(serverName)
        if (!(scheme == "http" && serverPort == 80) && !(scheme == "https" && serverPort == 443)) {
            builder.append(":").append(serverPort)
        }
        builder.append(contextPath).append(servletPath)
        if (pathInfo != null) {
            builder.append(pathInfo)
        }
        if (queryString != null) {
            builder.append("?").append(queryString)
        }

        return builder.toString()
    }

}