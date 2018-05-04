//white noise on underlayer of everything (maybe not everything) OR mutliringarray
//rhythmic PortalofAir routines. 12312312 12312312 looped
Server.default = s = Server.internal.boot

//Synth Definitions
(
//Max's multi ring array
(
SynthDef("droneSound", {arg count;
//10, 60, 100, 404 are all frequencies for Ringz and harm is the number of impulses Dust is suppose to impulse
a = Mix.ar(Ringz.ar(Array.fill(10,{var harm; harm = count + 100; Dust.ar(harm, 0.1)}),
  [10, 60, 100, 204], 1, 0.3));
  Out.ar(1, a); Out.ar(0, a)
}).add //need an envelope
);

//Ring modulated white noise, reversed
(
SynthDef("ReversedPortal", {arg freq=440, sustain=1;

  var env;
  var at = 0.1;
  var rt = 0.5;
  env = Env.perc(sustain,0.01,1,4);
  //{env = Env([0,1,0],[4,sustain,0.01])};
  Out.ar(0,Ringz.ar(WhiteNoise.ar(0.025),freq,0.5)*EnvGen.kr(env,doneAction:2))
}).add
);

//Ring modulated white noise, not reversed
(
SynthDef("Portal", {arg freq=440, sustain=1;

  var env;
  var at = 0.1;
  var rt = 0.5;
  env = Env([0,1,0],[2,sustain,0.01]);
  Out.ar(0,Ringz.ar(WhiteNoise.ar(0.005),freq,0.5)*EnvGen.kr(env,doneAction:2));
  Out.ar(1,Ringz.ar(WhiteNoise.ar(0.005),freq,0.5)*EnvGen.kr(env,doneAction:2));
}).add
);
)

//Routines
(
(
a = Pseq([2000, 400, 380, 1000, 800, 780],inf).asStream;
c = Pseq([6, 4, 5, 6, 4, 5],inf).asStream;
r = Routine({
  Synth("droneSound");
  inf.do({ //get specific number of times to do this
    var l = c.next;
    var f = a.next;
    Synth(["ReversedPortal","Portal"].choose,[\freq, f, \sustain, l]);
    //l.postln;
    f.postln;
    //f2.postln;
    l-3.wait
  })

})
)

(
SynthDef("PortalofAir", {

  b = Mix.ar( Array.fill(8, {SinOsc.ar(Rand(200, 2000.0), 0, 0.05) } ) );
  Out.ar(0, b)
}).send(s)
)

