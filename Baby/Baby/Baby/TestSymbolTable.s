.data
newline: .asciiz "\n"
.text
main:
.data
x: .asciiz y
.text


.data
x: .word 0
.text

    move $t0, EmptyStack
    sw $t0, x

