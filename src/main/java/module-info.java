module MeidoFX {
    requires javafx.controls;
    requires java.logging;

    opens id.alphareso.meidofx;

    exports id.alphareso.meidofx.base.stages;
    exports id.alphareso.meidofx.base.handlers;
}
