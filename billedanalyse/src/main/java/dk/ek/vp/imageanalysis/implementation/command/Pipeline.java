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

    // convience metoder:
    //(these methods could be moved out of the pipeline as their own commands)
    public Pipeline threshold(double threshold, double replacementValue) {
        return add(new MapCommand(v -> v > threshold ? v : replacementValue));
    }
    public Pipeline threshold(double threshold) {
        return threshold(threshold, 0.0);
    }

    public Pipeline slope() {
        return add(new SlopeCommand());
    }

    // materialize forces all steps/commands in pipeline to be calculated
    // materialize(Pipeline)
    public static Pipeline materialize(Pipeline p) {
        return p.add(new MaterializeCommand());
    }
    public Pipeline materialize() {
        return add(new MaterializeCommand());
    }


    // === Execute ===
    // execute is different from materialize
    // in that it simply returns the entire chain of commands,
    // like a recipe CommandX(CommandA(CommandB(Raster))) - it does not calculate anything.
    @Override
    public Raster execute(Raster input) {
        Raster current = input;

        for (RasterCommand command : commands) {
            current = command.execute(current);
        }

        return current;
    }
}