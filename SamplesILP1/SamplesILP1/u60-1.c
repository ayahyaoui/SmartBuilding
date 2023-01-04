#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object type_of;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp199; 
{ 
  ILP_Object ilptmp200; 
  ILP_Object ilptmp201; 
ilptmp200 = ILP_Integer2ILP(2); 
ilptmp201 = ILP_Integer2ILP(2); 
ilptmp199 = ILP_Plus(ilptmp200, ilptmp201);
} 
return ILP_type_of(ilptmp199);
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
