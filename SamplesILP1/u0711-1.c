#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp7; 
  ILP_Object ilptmp8; 
ilptmp7 = ILP_Integer2ILP(711); 
{ 
  ILP_Object ilptmp9; 
  ILP_Object ilptmp10; 
ilptmp9 = ILP_FALSE; 
ilptmp10 = ILP_Integer2ILP(2); 
ilptmp8 = ILP_Or(ilptmp9, ilptmp10);
} 
return ILP_Or(ilptmp7, ilptmp8);
} 

} 

static ILP_Object ilp_caught_program () {
  struct ILP_catcher* current_catcher = ILP_current_catcher;
  struct ILP_catcher new_catcher;

  if ( 0 == setjmp(new_catcher._jmp_buf) ) {
    ILP_establish_catcher(&new_catcher);
    return ilp_program();
  };
  return ILP_current_exception;
}

int main (int argc, char *argv[]) 
{ 
  ILP_START_GC; 
  ILP_print(ilp_caught_program()); 
  ILP_newline(); 
  return EXIT_SUCCESS; 
} 
