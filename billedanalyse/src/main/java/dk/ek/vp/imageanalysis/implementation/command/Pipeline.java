package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;
import dk.ek.vp.imageanalysis.interfaces.RasterPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Pipeline implements RasterPipeline {

    private final List<RasterCommand> commands;
    private Pipeline() {
        this.commands = new ArrayList<>();
    }

    // === Factory method ===
    public static Pipeline empty() {
        return new Pipeline();
    }

    // === Add command to command list/pipeline ===
    public Pipeline add(RasterCommand command) {
        commands.add(command);
        return this;
    }

    // === Execute ===
    @Override
    public Raster execute(Raster input) {
        Raster current = input;

        for (RasterCommand command : commands) {
            current = command.execute(current);
        }

        return current;
    }
}