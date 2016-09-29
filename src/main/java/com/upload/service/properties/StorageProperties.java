package com.upload.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The Class StorageProperties.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /** Localização do diretório raiz para armazenar arquivos. */
    private String location = "upload-dir";

    /**
	 * Gets the location.
	 *
	 * @return the location
	 */
    public String getLocation() {
        return location;
    }

    /**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
    public void setLocation(String location) {
        this.location = location;
    }

}
