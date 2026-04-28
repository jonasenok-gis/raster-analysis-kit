package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public final class RasterPipeline implements RasterCommand {

    private final List<RasterCommand> commands;
    // the setup with a private constructor and a factory method (empty())
    // means the pipeline list is immutable,
    // you cant by accident change parts of the pipeline
    // === Private constructor ===
    private RasterPipeline(List<RasterCommand> commands) {
        this.commands = Collections.unmodifiableList(commands);
    }

    // === Factory method ===
    public static RasterPipeline empty() {
        return new RasterPipeline(new ArrayList<>());
    }

    // === Add command (returns NEW pipeline) ===
    public RasterPipeline add(RasterCommand command) {
        List<RasterCommand> newCommands = new ArrayList<>(this.commands);
        newCommands.add(command);
        return new RasterPipeline(newCommands);
    }
    /*
    // === Convenience methods ===
    public RasterPipeline map(DoubleUnaryOperator operator) {
        return add(new MapCommand(operator));
    }

    public RasterPipeline threshold(double threshold) {
        return add(new MapCommand(v -> v > threshold ? 1.0 : 0.0));
    }
    */

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