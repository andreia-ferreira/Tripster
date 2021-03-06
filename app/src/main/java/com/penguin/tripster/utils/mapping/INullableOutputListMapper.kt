package com.penguin.tripster.utils.mapping

// Non-nullable to Nullable
interface NullableOutputListMapper<I, O>:
    IMapper<List<I>, List<O>?>

class NullableOutputListMapperImpl<I, O>(
        private val mapper: IMapper<I, O>
) : NullableOutputListMapper<I, O> {
    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}