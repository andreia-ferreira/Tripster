package com.penguin.tripster.utils.mapping

interface IMapper<I, O> {
    fun map(input: I): O
}