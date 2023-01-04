#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object to_string;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp172; 
  ILP_Object ilptmp173; 
{ 
  ILP_Object ilptmp174; 
ilptmp174 = ILP_Integer2ILP(12); 
ilptmp172 = ILP_to_string(ilptmp174);
}
ilptmp173 =  ILP_String2ILP("*"); 
return ILP_Plus(ilptmp172, ilptmp173);
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
