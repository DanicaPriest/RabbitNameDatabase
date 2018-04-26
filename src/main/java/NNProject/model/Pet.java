package NNProject.model;


import NNProject.model.Name;

import javax.print.attribute.standard.Media;

public class Pet {
    Name name;

    Media media;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
