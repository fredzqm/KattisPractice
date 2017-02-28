################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../srccpp/multigram.cpp 

OBJS += \
./srccpp/multigram.o 

CPP_DEPS += \
./srccpp/multigram.d 


# Each subdirectory must supply rules for building sources it contributes
srccpp/%.o: ../srccpp/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -std=c++11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


