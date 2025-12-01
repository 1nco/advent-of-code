#!/bin/zsh

gradle --warning-mode none clean build && java -jar build/libs/advent-of-code.jar