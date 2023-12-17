.data
newline: .asciiz "\n"
.text
main:
    li $t0 -5
    li $t1 -1
    add $t2 $t0 $t1

.data
x: .word 0
.text

    sw $t2, x


    li $v0, 10
    syscall

