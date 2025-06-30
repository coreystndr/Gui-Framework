package de.icor.frameworks.command;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    String description() default "";
    String usage() default "";
    String permission() default "";
    String[] aliases() default {};
}
