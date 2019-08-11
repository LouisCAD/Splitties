// Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.

#import <Foundation/Foundation.h>

@protocol KeyValueObserver;

@interface KeyValueObserverWrapper : NSObject

- (instancetype)initWithTargetKeyPath:(NSString *)targetKeyPath object:(NSObject *)object observer:(id <KeyValueObserver>)observer options:(NSKeyValueObservingOptions)options;

- (void)invalidate;

@end
